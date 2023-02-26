package crolopez.thecrmservice.shared.infrastructure.controllers.utils;

import javax.servlet.http.HttpServletRequest;

public interface RequestManager {
    HttpServletRequest getCurrentRequest();
}
