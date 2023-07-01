package br.com.jonatas.client.controllers;

import br.com.jonatas.client.services.ProduceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/produce")
public class ProduceController {

  @Autowired
  private ProduceService produceService;

  @PostMapping
  @ResponseStatus(code = HttpStatus.OK)
  Map<String, String> hello(@RequestBody String message) {
    String msg = this.produceService.sendMessage(message);

    Map<String, String> response = Map.of("message", msg);

    return response;
  }

}
