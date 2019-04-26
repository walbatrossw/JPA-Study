package com.doubles.jpa07.mapped_super_class;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;

//@Entity
@AttributeOverrides({ // 상속 받은 칼럼명을 다수 변경하고 싶을 경우
        @AttributeOverride(name = "id", column = @Column(name = "SELLER_ID")),      // 상속 받은 칼럼명 재정의
        @AttributeOverride(name = "name", column = @Column(name = "SELLER_NAME"))   // 상속 받은 칼럼명 재정의
})
public class Seller extends BaseEntity {

    // 식별자, 이름 상속

    // 샵 이름
    private String shopName;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
