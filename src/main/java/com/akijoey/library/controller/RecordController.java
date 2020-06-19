package com.akijoey.library.controller;

import com.akijoey.library.service.RecordService;
import com.akijoey.library.util.ResultUtil;
import com.akijoey.library.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/record")
public class RecordController {

    @Autowired
    RecordService recordService;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    ResultUtil resultUtil;

    @GetMapping("/count")
    public Map<String, Object> getCount(@RequestHeader("Authorization") String authorization) {
        String username = tokenUtil.getSubject(authorization.replace("Bearer ", ""));
        long count = recordService.getCountByUsername(username);
        return resultUtil.successResult("Get Success", Map.of("count", count));
    }
    @GetMapping("/count/{uid}")
    public Map<String, Object> getCount(@PathVariable("uid") int uid) {
        long count = recordService.getCountByUserId(uid);
        return resultUtil.successResult("Get Success", Map.of("count", count));
    }

    @GetMapping("/list/{page}/{size}")
    public Map<String, Object> getList(@RequestHeader("Authorization") String authorization, @PathVariable("page") int page, @PathVariable("size") int size) {
        String username = tokenUtil.getSubject(authorization.replace("Bearer ", ""));
        List<Map<String, Object>> list = recordService.getListByUsername(page, size, username);
        return resultUtil.successResult("Get Success", Map.of("list", list));
    }
    @GetMapping("/list/{page}/{size}/{uid}")
    public Map<String, Object> getList(@PathVariable("page") int page, @PathVariable("size") int size, @PathVariable("uid") int uid) {
        List<Map<String, Object>> list = recordService.getListByUserId(page, size, uid);
        return resultUtil.successResult("Get Success", Map.of("list", list));
    }

    @GetMapping("/total")
    public Map<String, Object> getTotal() {
        long total = recordService.getTotal();
        return resultUtil.successResult("Get Success", Map.of("total", total));
    }

    @GetMapping("/table/{page}/{size}")
    public Map<String, Object> getTable(@PathVariable("page") int page, @PathVariable("size") int size) {
        List<Map<String, Object>> table = recordService.getTable(page, size);
        return resultUtil.successResult("Get Success", Map.of("table", table));
    }

    @PostMapping("/borrow")
    public Map<String, Object> borrowing(@RequestHeader("Authorization") String authorization, @RequestBody Map<String, Long> data) {
        String username = tokenUtil.getSubject(authorization.replace("Bearer ", ""));
        recordService.insertRecord(username, data.get("isbn"));
        return resultUtil.successResult("Borrow Success");
    }

    @PostMapping("/return")
    public Map<String, Object> returning(@RequestBody Map<String, Integer> data) {
        recordService.updateState(data.get("id"));
        return resultUtil.successResult("Return Success");
    }

    @PostMapping("/renew")
    public Map<String, Object> renewing(@RequestBody Map<String, Object> data) {
        recordService.updateReturn((int)data.get("id"), (long)data.get("timestamp"));
        return resultUtil.successResult("Renew Success");
    }

    @PostMapping("/delete")
    public Map<String,Object> delete(@RequestBody Map<String, Integer> data) {
        recordService.deleteRecordById(data.get("id"));
        return resultUtil.successResult("Delete Success");
    }

}
