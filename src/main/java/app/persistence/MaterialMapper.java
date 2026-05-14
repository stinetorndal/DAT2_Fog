package app.persistence;

import app.exceptions.DatabaseException;
import app.entities.Material;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialMapper {

    //TODO skal vi overhovedet bruge denne her metode??? Bliver den ikke ret tung?
    public List<Material> getAllMaterials(ConnectionPool connectionPool) throws DatabaseException {
        List<Material> allMaterials = new ArrayList<>();

        String sql = "select * from materials order by materials_id";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int materialId = rs.getInt("material_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String unit = rs.getString("unit");
                double pricePerUnit = rs.getDouble("price_per_unit");

                allMaterials.add(new Material(materialId, name, description, unit, pricePerUnit));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl. Kunne ikke hente materialer." + e.getMessage());
        }
        return allMaterials;
    }

    public List<Material> getMaterialsByCategory(MaterialCategory category, ConnectionPool connectionPool) throws DatabaseException {
        List<Material> materialByCategory = new ArrayList<>();

        String sql = "SELECT m.material_id, m.name, m.description, m.unit, m.price_per_unit FROM materials m" +
                " JOIN material_category_link mcl ON m.material_id = mcl.material_id" +
                " JOIN material_categories mc ON mcl.category_id = mc.category_id" +
                " WHERE mc.category_name = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, category.name());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int materialId = rs.getInt("material_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String unit = rs.getString("unit");
                double pricePerUnit = rs.getDouble("price_per_unit");

                materialByCategory.add(new Material(materialId, name, description, unit, pricePerUnit));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl. Kunne ikke finde materialer." + e.getMessage());
        }
        return materialByCategory;
    }
}