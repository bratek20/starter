package pl.bratek20.commons.multirequest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MultiRequestController {
    private final MultiRequestService multiRequestService;

    @PostMapping("/multi-request")
    public String handleMultiRequest() {
        return multiRequestService.handleMultiRequest();
    }
}
