package app.persistence;

import app.entities.Customer;
import app.entities.Zipcode;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ZipcodeMapper {

    public Zipcode getZipcodeAndCity(int zipcode, ConnectionPool connectionPool) throws DatabaseException {
        Zipcode zipcodeObject = null;

        String sql = "SELECT * FROM zipcodes" +
                " WHERE zipcode = ?"
                ;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, zipcode);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int zip= rs.getInt("zipcode");
                String city = rs.getString("city");

                zipcodeObject = new Zipcode(zip, city);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Databasefejl: ", e.getMessage());
        }
        return zipcodeObject;
    }
}
