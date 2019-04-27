package com.jeffy.sqlautoreview.enums;

/**  
 * 是否常量枚举类  
 * @ClassName: YesNoEnum    
 * @author 陈剑飞    
 * @date 2016年3月28日 下午1:15:01    
 * @version  v 1.0    
 */
public enum YesNoEnum {
    YES("1","是"),
    NO("0","否");
    private String type;
    private String desc;
    
    private YesNoEnum(String type,String desc){
        this.type = type;
        this.desc = desc;
    }
    
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    /**
     * 根据类型得到枚举类型
     * @param type
     * @return YesNoEnum
     */
    public static YesNoEnum getYesNoType(String type) {
        for (YesNoEnum yesNoType : YesNoEnum.values()) {
            if (yesNoType.getType().equals(type)) {
                return yesNoType;
            }
        }
        return null;
    }
}
