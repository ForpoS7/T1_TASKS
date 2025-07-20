package t1.homeworks.bishopprototype.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import t1.homeworks.bishopprototype.service.CommandService;
import t1.homeworks.synthetichumancore.entity.Command;

@RestController
@RequestMapping("/api/v1/commands")
public class CommandController {
    private final CommandService commandService;

    @Autowired
    public CommandController(CommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping("/{androidName}")
    public ResponseEntity<String> executeCommand(
            @PathVariable String androidName,
            @RequestBody @Valid Command command) {

        commandService.executeCommand(androidName, command);
        return ResponseEntity.ok("Command executed by " + androidName);
    }

    @GetMapping("/{androidName}/overflow")
    public ResponseEntity<Void> testQueueOverflow(@PathVariable String androidName) {
        commandService.testQueueOverflow(androidName);
        return ResponseEntity.ok().build();
    }
}
