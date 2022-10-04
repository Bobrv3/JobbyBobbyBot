package org.bobrov.JobbyBobby.proxy;

import org.bobrov.JobbyBobby.model.HHresponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(name = "vacancy", url = "https://api.hh.ru")
public interface HHProxy {
  @GetMapping("/vacancies")
  HHresponse findVacancies(
          @SpringQueryMap Map<String, String> params);
}
