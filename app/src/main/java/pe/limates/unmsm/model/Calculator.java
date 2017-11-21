package pe.limates.unmsm.model;

public class Calculator {

    private String mItemDesc;
    private Double mItemWeight;
    private Double mItemGrade;

    public Calculator() {

    }

    public String getItemDesc() {
        return mItemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.mItemDesc = itemDesc;
    }

    public Double getItemWeight() {
        return mItemWeight;
    }

    public void setItemWeight(Double itemWeight) {
        this.mItemWeight = itemWeight;
    }

    public Double getItemGrade() {
        return mItemGrade;
    }

    public void setItemGrade(Double itemGrade) {
        this.mItemGrade = itemGrade;
    }

}
