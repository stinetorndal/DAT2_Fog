package app.persistence;

import app.enums.MaterialCategory;
import app.exceptions.DatabaseException;
import app.entities.Material;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialMapper {

    public List<Material> getAllMaterials(ConnectionPool connectionPool) throws DatabaseException {
        List<Material> allMaterials = new ArrayList<>();

        String sql = "SELECT * FROM materials ORDER BY materials_id";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Material material = createMaterialObject(rs);
                allMaterials.add(material);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl. Kunne ikke hente materialer." + e.getMessage());
        }
        return allMaterials;
    }

    public List<Material> getMaterialsByCategory(MaterialCategory category, ConnectionPool connectionPool) throws DatabaseException {
        List<Material> materialByCategory = new ArrayList<>();

        String sql = "SELECT m.material_id, m.name, m.length, m.description, m.unit, m.price_per_unit FROM materials m" +
                " JOIN material_category_link mcl ON m.material_id = mcl.material_id" +
                " JOIN material_categories mc ON mcl.category_id = mc.category_id" +
                " WHERE mc.category_name = ?" +
                "ORDER BY length";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, category.name());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Material material = createMaterialObject(rs);
                materialByCategory.add(material);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl. Kunne ikke finde materialer." + e.getMessage());
        }
        return materialByCategory;
    }

    private Material createMaterialObject(ResultSet rs) throws SQLException {

        int materialId = rs.getInt("material_id");
        String name = rs.getString("name");
        int length = rs.getInt("length");
        String description = rs.getString("description");
        String unit = rs.getString("unit");
        double pricePerUnit = rs.getDouble("price_per_unit");

        return new Material(materialId, name, length, description, unit, pricePerUnit);
    }
}