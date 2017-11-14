package pe.limates.unmsm.model;

public class Calculator {

    private int mItemDescId;
    private int mItemWeightId;
    private Double mItemGrade;

    public Calculator(int itemDescId, int itemWeightId) {
        mItemDescId = itemDescId;
        mItemWeightId = itemWeightId;
    }

    public int getItemDescId() {
        return mItemDescId;
    }

    public int getItemWeightId() {
        return mItemWeightId;
    }

    public Double getItemGrade() {
        return mItemGrade;
    }

    public void setItemGrade(Double itemGrade) {
        this.mItemGrade = itemGrade;
    }

}
