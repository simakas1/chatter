package lt.vaskevicius.chatter.controller;

import lt.vaskevicius.chatter.domain.dto.response.GlobalAnalyticsResponse;
import lt.vaskevicius.chatter.domain.dto.response.UserAnalyticsResponse;
import lt.vaskevicius.chatter.service.AnalyticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticService analyticService;

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserAnalyticsResponse getUserAnalytics(@PathVariable int id) {
        return analyticService.getUserAnalytics(id);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public GlobalAnalyticsResponse getAnalytics() {
        return analyticService.getGlobalAnalytics();
    }
}
