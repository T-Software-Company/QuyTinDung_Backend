package com.tsoftware.qtd.constants.EnumType;


import lombok.Getter;

@Getter
public enum IncomeProofType {

        SALARY_SLIP(" Phiếu lương"),
        BANK_STATEMENT("Sao kê ngân hàng"),
        TAX_RETURN("Tờ khai thuế"),
        INVESTMENT_PROOF("Chứng nhận đầu tư"),
        RENTAL_INCOME("Thu nhập từ cho thuê"),
        OTHER("Các loại khác");
        private final String description;
        IncomeProofType(String description){
            this.description =description;
    }
}
