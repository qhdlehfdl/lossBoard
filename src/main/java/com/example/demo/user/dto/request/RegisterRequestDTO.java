package com.example.demo.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDTO {
    @NotBlank(message = "아이디는 필수 입력 항목입니다.")
    @Size(min = 3, max = 20, message = "아이디는 3자 이상 20자 이하로 입력해야 합니다.")
    private String id;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(min = 3, max = 20, message = "비밀번호는 3자 이상 20자 이하로 입력해야 합니다.")
    private String password;

    @NotBlank(message = "닉네임은 필수 입력 항목입니다.")
    @Size(min = 3, max = 20, message = "닉네임은 3자 이상 20자 이하로 입력해야 합니다.")
    private String nickname;

    private String name;

    @NotNull(message = "학번은 필수 입력 항목입니다.")
    private Integer studentID;
}
