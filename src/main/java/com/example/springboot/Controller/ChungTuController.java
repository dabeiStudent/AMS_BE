package com.example.springboot.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.DAO.*;
import com.example.springboot.BusinessLogic.*;
import com.example.springboot.Model.*;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chung-tu")
public class ChungTuController {

    private final ChungTuServices chungTuService;
    private final UserServices userService;
    private final JWTGenerator jwtGen;
    @Autowired
    public ChungTuController(ChungTuServices chungTuService, UserServices userService, JWTGenerator jwtGen) {
        this.chungTuService = chungTuService;
        this.userService= userService;
        this.jwtGen = jwtGen;
    }

    @GetMapping("/all/{current}/{token}")
    public List<ChungTuModel> getAllChungTus(@PathVariable(value="current") int page, @PathVariable(value="token") String token) {
    	Map<String, Object>deCrypted = jwtGen.tokenDecrypt(token);
//    	System.out.println(deCrypted.get("user"));
    	String user = deCrypted.get("user").toString();
    	return chungTuService.getAllChungTus(user);
    }
    @GetMapping("/all-to-approve/{current}/{token}")
    public List<ChungTuModel> getAllChungTuDuyet(@PathVariable(value="current") int page, @PathVariable(value="token") String token) {
    	Map<String, Object>deCrypted = jwtGen.tokenDecrypt(token);
//    	System.out.println(deCrypted.get("user"));
    	String user = deCrypted.get("user").toString();
    	return chungTuService.getAllChungTuDuyet(user);
    }
    @GetMapping("/nhat-ki/{maCT}")
    public List<TrangThaiModel> getNhatKiChungTu(@PathVariable(value="maCT") String maCT) {
    	return chungTuService.getNhatKiChungTu(maCT);
    }
    @GetMapping("/noi-dung/{maCT}")
    public ChiTietCTModel getChiTietChungTu(@PathVariable(value="maCT") String maCT) {
    	return chungTuService.getChiTietChungTu(maCT);
    }
    @GetMapping("/ket-qua-duyet/{maCT}")
    public List<KetQuaModel> getKetQuaChungTu(@PathVariable(value="maCT") String maCT){
    	return chungTuService.getKetQuaChungTu(maCT);
    }
    @GetMapping("/lay-ten-nguoi-duyet/{id}")
    public String getTenNguoiDuyet(@PathVariable(value="id") String id) {
    	return chungTuService.getTenNguoiDuyet(id);
    }
    @PostMapping("/chon-nguoi-duyet")
    public ResponseEntity<String> postChonNguoiDuyet(@RequestBody YeuCauChungTu yeuCau) throws JsonProcessingException{
    	return chungTuService.postChonNguoiDuyet(yeuCau);
    }
    @PostMapping("/tao-moi-chung-tu")
    public ResponseEntity<String> postYeuCauChungTu(@RequestBody YeuCauChungTu yeuCau) {
    	System.out.println(yeuCau.getMaLoai());
    	List<Map<String, Object>> nguoiDuyetList = yeuCau.getNguoiDuyet();
    	if(yeuCau.getMaForm().equals(null) 
    			|| yeuCau.getNoiDung().size()== 0
    			|| yeuCau.getMaForm().isEmpty()
    			|| yeuCau.getMaLoai().isEmpty()
    			|| yeuCau.getNguoiTao().isEmpty()
    			|| nguoiDuyetList.size() == 0)
    	{
    		return ResponseEntity.status(404).body("Vui lòng nhập đủ thông tin: "+ yeuCau.getMaLoai()+ yeuCau.getMaForm()+yeuCau.getNguoiTao()+yeuCau.getNoiDung()+yeuCau.getNguoiDuyet()+yeuCau.getThoiGianTao() );
    	}
    	return chungTuService.postYeuCauChungTu(yeuCau);
    }
    @GetMapping("/huy-chung-tu/{maCT}")
    public ResponseEntity<String> cancelChungTu(@PathVariable(value="maCT") String maCT){
    	return chungTuService.cancelChungTu(maCT);
    }
    @GetMapping("/get-loai-chung-tu/{page}")
    public List<LoaiChungTuModel> getAllLoaiCT(@PathVariable(value="page") int page){
    	return chungTuService.getAllLoaiCT();
    }
    @GetMapping("/get-form-field/{formId}")
    public List<FormFieldModel> getAllFormFields(@PathVariable(value="formId") String formId){
    	return chungTuService.getAllFormFields(formId);
    }
    //Test
    
}
