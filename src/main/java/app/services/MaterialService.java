package app.services;

import app.entities.Material;
import app.enums.MaterialCategory;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.MaterialMapper;

import java.util.List;

public class MaterialService {

    private MaterialMapper materialMapper = new MaterialMapper();

    public List<Material> getMaterialsByCategory(MaterialCategory category, ConnectionPool connectionPool) throws DatabaseException {
        return materialMapper.getMaterialsByCategory(category,connectionPool);
    }
}