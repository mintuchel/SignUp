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
@Tag(name = "관리자 API",
        description = "테스트 시 유용하게 사용하되, 다른 사람의 자원은 절대 삭제하지 마세요.\n\n" +
                "관리자 권한 남용 시 타 동기들에게 피해가 갈 수 있습니다.")
public class AdminController {
    private final AdminService adminService;

    // GET 할때는 @PathVariable을 사용해도 되고 @RequestParam을 사용해도 된다.
    // 1. @PathVariable = 리소스 식별자(ID나 고유한 값)를 URL 경로에 포함시켜서 해당 리소스를 식별하고 조회할 때
    // ex) 특정 게시글을 클릭했을때 해당 게시글의 고유ID인 숫자를 타고 들어갈때
    // 2. @RequestParam = 필터링 또는 검색을 할 때, 여러 개의 파라미터를 key=value 형식으로 전달할 때

    // 여기서는 필터링은 아니지만 내가 특정 유저를 조회하는 것이므로 @RequestParam을 사용하는 것이 더 맞는 것 같다.
    // 내가 만약 이 사람의 DB 내 고유 PK 값인 auto-increment 된 정수로 조회를 하는거면 @PathVariable이 맞지만 여기서는 내가 이메일로 검색을 하는 것이므로 ㅇㅇ
    // 아 근데 이메일은 unique 한 값이기도 하고 특정 리소스를 식별할 수 있는 값이니까 @PathVariable 로 해놓는다 그냥
    // auto-increment 인 PK 값으로 하면 전체 조회 때리고 자기 번호 찾아서 삭제해야하므로 번거로우니까 그냥 이메일로 바로 삭제가능하도록 함
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
        // NO_CONTENT는 삭제 작업이 성공적으로 처리되었음을 나타내지만, 반환할 데이터가 없다는 의미 -> 라고 한다...
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/email-verification/{email}")
    @Operation(summary = "이메일 인증 기록 삭제")
    public ResponseEntity<Void> deleteEmail(@PathVariable String email) {
        adminService.deleteEmail(email);

        // 왜 삭제 시 NO_CONTENT로 하는 것일까?
        // 삭제 성공 여부를 반환하면 안되는 것인가??
        // NO_CONTENT는 삭제 작업이 성공적으로 처리되었음을 나타내지만, 반환할 데이터가 없다는 의미 -> 라고 한다...
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
