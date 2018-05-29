package by.bytechs.ui.restService;

import by.bytechs.dto.TerminalDTO;
import by.bytechs.dto.TerminalInfoDTO;
import by.bytechs.ui.restService.interfaces.TerminalRestService;
import by.bytechs.ui.restService.utils.RestUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * @author Romanovich Andrei
 */
@Service
public class TerminalRestServiceImpl implements TerminalRestService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    @Qualifier(value = "getAllTerminals")
    private String getAllTerminals;
    @Autowired
    @Qualifier(value = "getAllTerminalsInfo")
    private String getAllTerminalsInfo;
    private final Logger errorLogger = LogManager.getLogger(TerminalRestServiceImpl.class);

    @Override
    public List<TerminalDTO> getAllTerminals() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", "application/json;charset=utf-8");
            HttpEntity<String> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(getAllTerminals, HttpMethod.GET,
                    httpEntity, String.class);
            TypeReference<List<TerminalDTO>> typeReference = new TypeReference<List<TerminalDTO>>() {
            };
            RestUtil<List<TerminalDTO>> restUtil = new RestUtil<>();
            return restUtil.getResultActionCollection(responseEntity, typeReference);
        } catch (RestClientException e) {
            StackTraceElement[] stacks = e.getStackTrace();
            errorLogger.error(Arrays.toString(stacks));
        }
        return null;
    }

    @Override
    public List<TerminalInfoDTO> getAllTerminalInfoDto() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", "application/json;charset=utf-8");
            HttpEntity<String> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(getAllTerminalsInfo, HttpMethod.GET,
                    httpEntity, String.class);
            TypeReference<List<TerminalInfoDTO>> typeReference = new TypeReference<List<TerminalInfoDTO>>() {
            };
            RestUtil<List<TerminalInfoDTO>> restUtil = new RestUtil<>();
            return restUtil.getResultActionCollection(responseEntity, typeReference);
        } catch (RestClientException e) {
            StackTraceElement[] stacks = e.getStackTrace();
            errorLogger.error(Arrays.toString(stacks));
        }
        return null;
    }
}
