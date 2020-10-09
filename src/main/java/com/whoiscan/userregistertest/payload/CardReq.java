package com.whoiscan.userregistertest.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardReq {
    @Pattern(regexp = "[0-9]{16,16}", message = "Number must be 16 number")
    private String number;
    @Size(max = 4, min = 4, message = "Enter expire date mm/yy")
    private String expireDate;
    @NotBlank
    private String bank;
}
