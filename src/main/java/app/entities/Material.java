package app.entities;

public class Material {
    private int materialId;
    private String name;
    private String description;
    private String unit;
    private double pricePerUnit;

    public Material(int materialId, String name, String description, String unit, double pricePerUnit) {
        this.materialId = materialId;
        this.name = name;
        this.description = description;
        this.unit = unit;
        this.pricePerUnit = pricePerUnit;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }
}
