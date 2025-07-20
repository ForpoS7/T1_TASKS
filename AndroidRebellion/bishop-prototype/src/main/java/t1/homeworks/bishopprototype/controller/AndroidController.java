package t1.homeworks.bishopprototype.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import t1.homeworks.bishopprototype.service.AndroidService;

@RestController
@RequestMapping("/api/v1/android")
public class AndroidController {
    private final AndroidService androidService;

    @Autowired
    public AndroidController(AndroidService androidService) {
        this.androidService = androidService;
    }

    @GetMapping("/{name}/executed-tasks")
    public ResponseEntity<Integer> getExecutedTasks(@PathVariable String name) {
        Integer executedTasks = androidService.getExecutedTasks(name);
        if (executedTasks == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(executedTasks);
    }

    @GetMapping("/{name}/status")
    public ResponseEntity<String> getStatus(@PathVariable String name) {
        String status = androidService.getStatus(name);
        if (status == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Android " + name + " is " + status);
    }

    @PostMapping("/{name}/shutdown")
    public ResponseEntity<String> shutdown(@PathVariable String name) {
        String result = androidService.shutdown(name);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Android " + result + " has been shut down successfully and removed from the system.");
    }
}
