package com.ssafy.controller;

import com.ssafy.model.dto.relation.RelationSaveRequestDto;
import com.ssafy.model.dto.relation.RelationUpdateRequestDto;
import com.ssafy.model.service.RelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = { "Relation" })
@RestController
@RequestMapping("/relations")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class RelationRestController {
	@Autowired
	RelationService rService;
	
	@ApiOperation("인물 관계 모두 조회")
	@GetMapping()
	ResponseEntity<Map<String, Object>> getRelations(){
		return handleSuccess(rService.getRelations());
	}
	
	@ApiOperation("인물 관계 하나 조회")
	@GetMapping("/{relationPk}")
	ResponseEntity<Map<String, Object>> getRelation(@PathVariable int relationPk){
		return handleSuccess(rService.getRelation(relationPk));
	}
	
	@ApiOperation("한 소설의 인물 관계 조회")
	@GetMapping("/novelPk={novelPk}")
	ResponseEntity<Map<String, Object>> getRelationsByNovel(@PathVariable int novelPk){
		return handleSuccess(rService.getRelationsByNovel(novelPk));
	}
	
	@ApiOperation("인물 관계 생성")
	@PostMapping()
	ResponseEntity<Map<String, Object>> registRelation(@RequestBody RelationSaveRequestDto requestDto){
		return handleSuccess(rService.registRelation(requestDto));
	}
	
	@ApiOperation("인물 관계 수정")
	@PutMapping("/{relationPk}")
	ResponseEntity<Map<String, Object>> updateRelation(@PathVariable int relationPk, @RequestBody RelationUpdateRequestDto requestDto){
		return handleSuccess(rService.updateRelation(relationPk, requestDto));
	}
	
	@ApiOperation("인물 관계 삭제")
	@DeleteMapping("/who/{who}/whom/{whom}")
	ResponseEntity<Map<String, Object>> deleteRelation(@PathVariable int who, @PathVariable int whom){
		rService.deleteRelation(who, whom);
		return handleSuccess("인물 관계 삭제 성공");
	}
	
	public ResponseEntity<Map<String, Object>> handleSuccess(Object data) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "ok");
		resultMap.put("data", data);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}
	
	public ResponseEntity<Map<String, Object>> handleFail(Object data, HttpStatus status) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "fail");
		resultMap.put("data", data);
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}
