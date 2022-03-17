package com.javabootcamp.assessment2.features.dashboard;

import com.javabootcamp.assessment2.controllers.SecuredRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/secure/dashboard")
public class DashboardController extends SecuredRestController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public DashboardResponse getDashboardOverview(@RequestParam UUID shipmentId) {
        DashboardResponse dashboardOverview = dashboardService.getDashboardOverview(shipmentId);
        return dashboardOverview;
    }

}
