package enstudy.signup.domain.admin.api;

import enstudy.signup.domain.admin.dto.response.UserInfoResponse;
import enstudy.signup.domain.admin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@Tag(name = "관리자 API", description = "테스트 시 필요할만한 API 모음")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/user/{email}")
    @Operation(summary = "특정 유저 조회")
    public ResponseEntity<UserInfoResponse> getUserByEmail(@PathVariable String email) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(adminService.getUserByEmail(email));
    }

    @GetMapping("/user")
    @Operation(summary = "모든 유저 조회")
    public ResponseEntity<List<UserInfoResponse>> getAllUsers() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(adminService.getAllUsers());
    }

    @DeleteMapping("/user/{email}")
    @Operation(summary = "유저 삭제")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        adminService.deleteUserByEmail(email);

        // 왜 삭제 시 NO_CONTENT로 하는 것일까?
        // 삭제 성공 여부를 반환하면 안되는 것인가??
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/email/{email}")
    @Operation(summary = "인증 요청한 이메일 삭제")
    public ResponseEntity<Void> deleteEmail(@PathVariable String email) {
        adminService.deleteEmail(email);

        // 왜 삭제 시 NO_CONTENT로 하는 것일까?
        // 삭제 성공 여부를 반환하면 안되는 것인가??
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
