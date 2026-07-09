package co.istad.ite.features.auth.service;

import co.istad.ite.features.auth.dto.request.RegisterRequest;
import co.istad.ite.features.auth.dto.resopnse.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest registerRequest);
}
