package cn.damei.enumeration.oa;

public enum DEPTYPE {
    LIABLEDEPARTMENT("责任部门"),FILIALECUSTOMERSERVICE("分公司客管部"),
    GROUPCUSTOMERSERVICE("集团客管部"),SUPPLIER("供应商");

    private String label;

    DEPTYPE(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
