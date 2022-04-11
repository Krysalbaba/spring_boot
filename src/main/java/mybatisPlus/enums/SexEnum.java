package mybatisPlus.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SexEnum  implements IEnum<Integer> {
    WOMAN(0,"女"),
    MAN(1,"男");

    private int value ;

    @JsonValue
    private String desc ;

    SexEnum(int value,String desc){
        this.value=value ;
        this.desc=desc ;
    }
    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.desc;
    }


}
