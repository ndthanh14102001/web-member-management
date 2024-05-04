import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.util.List;

@RestController
public class ProcessingController {

    private final ProcessingService processingService;

    @Autowired
    public ProcessingController(ProcessingService processingService) {
        this.processingService = processingService;
    }

    @GetMapping("/processing")
    public List<Object[]> getProcessingList(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return processingService.getProcessingList(type, startDate, endDate);
    }
}

