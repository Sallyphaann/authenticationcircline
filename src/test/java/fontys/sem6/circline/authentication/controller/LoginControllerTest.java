package fontys.sem6.circline.authentication.controller;

import fontys.sem6.circline.authentication.business.LoginUseCase;
import fontys.sem6.circline.authentication.domain.LoginRequest;
import fontys.sem6.circline.authentication.domain.LoginResponse;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Tag("integration")
class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LoginUseCase loginUseCase;
    @InjectMocks
    private LoginController loginController;
    @Test
    void testLogin_success() throws Exception {
        LoginRequest loginRequest = LoginRequest.builder()
                .email("user@example.com")
                .password("Password1@")
                .build();

        LoginResponse loginResponse = LoginResponse.builder()
                .accessToken("valid-token")
                .build();

        when(loginUseCase.login(loginRequest)).thenReturn(loginResponse);
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType("application/json")
                        .content("{\"email\": \"user@example.com\", \"password\": \"Password1@\"}"))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").value("valid-token"));
        verify(loginUseCase).login(any(LoginRequest.class));
    }
    @Test
    void testLogin_invalidEmailFormat() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType("application/json")
                        .content("{\"email\": \"invalid-email\", \"password\": \"Password1@\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testLogin_missingFields() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType("application/json")
                        .content("{\"email\": \"user@example.com\"}"))
                .andExpect(status().isBadRequest());
    }

}

