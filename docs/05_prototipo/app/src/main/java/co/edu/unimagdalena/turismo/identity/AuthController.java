package co.edu.unimagdalena.turismo.identity;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/cuenta")
public class AuthController {
  private final AuthService auth;

  public AuthController(AuthService a) { this.auth = a; }

  @PostMapping("/registro")
  public Map<String, Object> registro(@RequestBody AuthService.RegistroReq req) {
    return auth.registro(req);
  }

  public record LoginReq(String correo, String password) {}

  @PostMapping("/login")
  public Map<String, Object> login(@RequestBody LoginReq req) {
    return auth.login(req.correo(), req.password());
  }
}
