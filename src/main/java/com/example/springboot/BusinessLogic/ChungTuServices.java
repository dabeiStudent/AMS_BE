package com.example.springboot.BusinessLogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.springboot.DAO.*;
import com.example.springboot.Model.*;
@Service
public class ChungTuServices {

    private final ChungTuDAO chungTuDAO;
    
    @Autowired
    public ChungTuServices(ChungTuDAO chungTuDAO) {
        this.chungTuDAO = chungTuDAO;
    }

    public List<ChungTuModel> getAllChungTus(String user) {
        return chungTuDAO.getAllChungTus(user);
    }
    public List<ChungTuModel> getAllChungTuDuyet(String user){
    	return chungTuDAO.getAllChungTuDuyet(user);
    }
    public Boolean checkAuthentication (String maCT, String user) {
    	return chungTuDAO.checkAuthentication(maCT, user);
    }
    public List<TrangThaiModel> getNhatKiChungTu(String maCT) {
    	return chungTuDAO.getNhatKiChungTu(maCT);
    }
    
    public ChiTietCTModel getChiTietChungTu(String maCT) {
    	return chungTuDAO.getChiTietChungTu(maCT);
    }
    public List<KetQuaModel> getKetQuaChungTu(String maCT){
    	return chungTuDAO.getKetQuaChungTu(maCT);
    }
    public String getTenNguoiDuyet (String id) {
    	return chungTuDAO.getTenNguoiDuyet(id);
    }
    @Transactional
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    public ResponseEntity<String> postYeuCauChungTu (YeuCauChungTu yeuCau) {
//    	System.out.println(yeuCau.getNguoiDuyet());
    	System.out.println(yeuCau.getMaForm());
    	List<Map<String,String>> nguoiDuyetCheckList = chungTuDAO.listCheckNguoiDuyet(yeuCau.getMaLoai());
//    	System.out.println(nguoiDuyetCheckList);
    	for(Map<String,Object> nguoiDuyetInput : yeuCau.getNguoiDuyet()) {
        	Boolean flag = false;
    		for(Map<String,String> nguoiDuyetCheck : nguoiDuyetCheckList) {
    			if(nguoiDuyetInput.get("user_update").toString().equals(nguoiDuyetCheck.get("id").toString()) && nguoiDuyetInput.get("lvl").toString().equals(nguoiDuyetCheck.get("lvl").toString())) {
    				flag = true;
    				break;
    			}
    			System.out.print(nguoiDuyetInput.get("user_update") + " " + nguoiDuyetCheck.get("id")+ " ");
    			System.out.println(nguoiDuyetInput.get("lvl") + " " + nguoiDuyetCheck.get("lvl"));
    		}
    		if(flag == false) {
    	   	     return ResponseEntity.status(400).body("Người duyệt không hợp lệ: "+ nguoiDuyetInput.get("user_update") + nguoiDuyetInput.get("lvl"));
    	    }
    	}
    	 try {
//    		System.out.println(yeuCau.getNguoiDuyet());
    	  	Jsonb jsonb = JsonbBuilder.create();
    	  	String jsonString = jsonb.toJson(yeuCau.getNoiDung());
    	  	yeuCau.setMaTT("TT001");
    	  	String maCT = chungTuDAO.postNewChungTu(yeuCau);
    	 	if (maCT == null) {
    	  	    throw new RuntimeException("Không thể thêm chứng từ mới");
    	  	}
    	  	yeuCau.setMaCT(maCT);
    	  	System.out.println(yeuCau.getMaCT());

    	  	Boolean checkInsertNoiDung = chungTuDAO.postChungTuNoiDung(yeuCau, jsonString);
    	  	if (!checkInsertNoiDung) {
    	  	    throw new RuntimeException("Không thể thêm nội dung");
    	  	}

    	  	Boolean checkInsertTrangThai = chungTuDAO.postChungTuTrangThai(yeuCau);
    	  	if (!checkInsertTrangThai) {
    	  	    throw new RuntimeException("Không thể thêm trạng thái");
    	  	}
    	  	Boolean checkInsertKetQua = chungTuDAO.postChungTuKetQua(yeuCau);
    	  	if (!checkInsertKetQua) {
    	  	    throw new RuntimeException("Không thể thêm kết quả");
    	  	}

    	    return ResponseEntity.status(200).body("Thêm thành công");
    	} catch (Exception e) {
    	     e.printStackTrace();
    	     return ResponseEntity.status(402).body("Có lỗi xảy ra: " + e.getMessage());
    	}
    }
    public ResponseEntity<String> postChonNguoiDuyet(YeuCauChungTu yeuCau) throws JsonProcessingException{
    	Map<String, String> keyLabel = chungTuDAO.getFormLabel(yeuCau);
    	String key = keyLabel.get("key");
    	String label = keyLabel.get("label");
//    	System.out.println(key + label);
    	//Form label de get value
    	String value = "";
    	Map<String, Object> noiDungMap = yeuCau.getNoiDung();
    	for (Map.Entry<String, Object> entry : noiDungMap.entrySet()) {
            String labelInput = entry.getKey();
            if(labelInput.equals(label)) {
            	value = entry.getValue().toString();
            }
        }
//    	System.out.println(value);
    	//Lay duoc value roi
    	//Dung key de tim dieu kien sau do so sanh voi value
    	System.out.println(yeuCau.getMaLoai());

    	List<Map<String,String>> conditions = chungTuDAO.getCondition(key,yeuCau.getMaForm(),yeuCau.getMaLoai());
//    	System.out.println(conditions);
    	//Voi du lieu la so thi parseInt de xu li
        int valueToCompare = Integer.parseInt(value);

    	 for (Map<String, String> condition : conditions) {
             String operator = condition.get("match");
             String comparedValueStr = condition.get("compared_value");
             String logic = condition.get("logic");
             String pair = condition.get("pair");
//             System.out.println(logic);
//             System.out.println(condition);
             
             // Chuyển đổi giá trị so sánh từ String sang số
             int comparedValue = Integer.parseInt(comparedValueStr);
             
             // Thực hiện so sánh dựa trên toán tử
             switch (operator) {
                 case "gt":
                     if (valueToCompare > comparedValue) {
                         System.out.println("Giá trị " + valueToCompare + " lớn hơn " + comparedValue);
                         List<Map<String,String>> approver = chungTuDAO.getApprover(key, operator, comparedValueStr, yeuCau.getMaForm());
                         ObjectMapper objectMapper = new ObjectMapper();
                         String approverJson = objectMapper.writeValueAsString(approver);
                             System.out.println("Đa co nguoi duyet");
                             return ResponseEntity.status(200).body(approverJson);        	
                     }
                     break;
                 case "lt":
                     if (valueToCompare < comparedValue) {
                         System.out.println("Giá trị " + valueToCompare + " nhỏ hơn " + comparedValue);
                         List<Map<String,String>> approver = chungTuDAO.getApprover(key, operator, comparedValueStr, yeuCau.getMaForm());
                         ObjectMapper objectMapper = new ObjectMapper();
                         String approverJson = objectMapper.writeValueAsString(approver);
//                         System.out.println(approver);
                             System.out.println("Đa co nguoi duyet");
                             return ResponseEntity.status(200).body(approverJson);
                     }
                     break;
                 case "eql":
                     if (valueToCompare == comparedValue) {
                         System.out.println("Giá trị " + valueToCompare + " bằng " + comparedValue);
                         List<Map<String,String>> approver = chungTuDAO.getApprover(key, operator, comparedValueStr, yeuCau.getMaForm());
                         ObjectMapper objectMapper = new ObjectMapper();
                         String approverJson = objectMapper.writeValueAsString(approver);
//                         System.out.println(approver);
                         System.out.println("Đa co nguoi duyet");
                             return ResponseEntity.status(200).body(approverJson);
                     }
                     break;
                 case "gte":
                	 if (valueToCompare > comparedValue || valueToCompare == comparedValue) {
                         System.out.println("Giá trị " + valueToCompare + " lớn hơn/bằng " + comparedValue);
                         List<Map<String,String>> approver = chungTuDAO.getApprover(key, operator, comparedValueStr, yeuCau.getMaForm());
                         ObjectMapper objectMapper = new ObjectMapper();
                         String approverJson = objectMapper.writeValueAsString(approver);
//                         System.out.println(approver);
                         System.out.println("Đa co nguoi duyet");
                             return ResponseEntity.status(200).body(approverJson);
                     }
                     break;
                 case "lte":
                	 if (valueToCompare < comparedValue || valueToCompare == comparedValue) {
                         System.out.println("Giá trị " + valueToCompare + " lớn hơn/bằng " + comparedValue);
                         List<Map<String,String>> approver = chungTuDAO.getApprover(key, operator, comparedValueStr, yeuCau.getMaForm());
                         ObjectMapper objectMapper = new ObjectMapper();
                         String approverJson = objectMapper.writeValueAsString(approver);
//                         System.out.println(approver);
                         System.out.println("Đa co nguoi duyet");
                             return ResponseEntity.status(200).body(approverJson);
                     }
                     break;
                 case "in":
                	 List<String> listComparedValue = chungTuDAO.pairValue(key, yeuCau.getMaLoai() , pair,  operator);
                	 System.out.println(listComparedValue);
                	 int value1 = Integer.parseInt(listComparedValue.get(0));
                	 int value2 = Integer.parseInt(listComparedValue.get(1));
                	 System.out.println(value1+" "+value2);
                	 System.out.println(valueToCompare);
                	
                	 if (value1 > value2) {
                         System.out.println("2 bé hơn 1");
                		 if (valueToCompare >= value2 && valueToCompare <= value1) {
                             System.out.println("Giá trị nằm trong khoảng: " + value2 + " tới " + value1);
                             List<Map<String,String>> approver1 = chungTuDAO.getApprover(key, operator, listComparedValue.get(0), yeuCau.getMaForm());
                             List<Map<String,String>> approver2 = chungTuDAO.getApprover(key, operator, listComparedValue.get(1), yeuCau.getMaForm());
                             if(approver1.size() == 0) {
                            	 ObjectMapper objectMapper = new ObjectMapper();
                                 String approverJson = objectMapper.writeValueAsString(approver2);
                                 System.out.println(approver2);
                                 System.out.println("Đa co nguoi duyet");
                                 return ResponseEntity.status(200).body(approverJson);
                             }else {
                            	 ObjectMapper objectMapper = new ObjectMapper();
                                 String approverJson = objectMapper.writeValueAsString(approver1);
                                 System.out.println(approver1);
                                 System.out.println("Đa co nguoi duyet");
                                 return ResponseEntity.status(200).body(approverJson);
                             }
                         }
                     } else if (value1 < value2) {
                         System.out.println("1 bé hơn 2");
                         if (valueToCompare >= value1 && valueToCompare <= value2) {
                             System.out.println("Giá trị nằm trong khoảng: " + value1 + " tới " + value2);
                             List<Map<String,String>> approver1 = chungTuDAO.getApprover(key, operator, listComparedValue.get(0), yeuCau.getMaForm());
                             List<Map<String,String>> approver2 = chungTuDAO.getApprover(key, operator, listComparedValue.get(1), yeuCau.getMaForm());
                             if(approver1.size() == 0) {
                            	 ObjectMapper objectMapper = new ObjectMapper();
                                 String approverJson = objectMapper.writeValueAsString(approver2);
                                 System.out.println(approver2);
                                 System.out.println("Đa co nguoi duyet");
                                 return ResponseEntity.status(200).body(approverJson);
                             }else {
                            	 ObjectMapper objectMapper = new ObjectMapper();
                                 String approverJson = objectMapper.writeValueAsString(approver1);
                                 System.out.println(approver1);
                                 System.out.println("Đa co nguoi duyet");
                                 return ResponseEntity.status(200).body(approverJson);
                             }
                         }
                     }
                	 System.out.println("2 bé hơn 1");
                     
                	 break;
                 default:
                     System.out.println("Toán tử không hợp lệ: " + operator);
             }
         }
   	 return ResponseEntity.status(400).body("Có lỗi");
    }
    public ResponseEntity<String> cancelChungTu(String maCT){
    	Boolean check = chungTuDAO.checkCT(maCT);
    	if(check.equals(false))
    	{
    		return ResponseEntity.status(404).body("Không hợp lệ");
    	}
    	Boolean checkKetQua = chungTuDAO.checkKetQua(maCT);
    	if(checkKetQua.equals(false)) {
    		return ResponseEntity.status(401).body("Chứng từ đã có người duyệt");
    	}
    	String nguoiCapNhat = chungTuDAO.getNguoiTao(maCT);
//    	System.out.println(nguoiCapNhat);
    	Boolean capNhatTrangThai = chungTuDAO.updateStatus(maCT,nguoiCapNhat);
//    	System.out.println(capNhatTrangThai);
    	return ResponseEntity.status(200).body("Đã hủy");
    }
    public List<LoaiChungTuModel> getAllLoaiCT(String user){
    	return chungTuDAO.getAllLoaiCT(user);
    }
    public List<FormFieldModel> getAllFormFields(String formId){
    	return chungTuDAO.getAllFormFields(formId);
    }
    public  ResponseEntity<String> processing(String maCT, String user){
    	try {
    	Boolean checkIsTurn = chungTuDAO.checkTurnApprove(maCT, user);
//    	System.out.println(checkIsTurn);
    	List<Map<String,String>> checkApproveKind = chungTuDAO.checkApproveKind(maCT,user);
    	Map<String,String> thisApprover = new HashMap<>();
//    	System.out.println(checkApproveKind);
    	List<Map<String,String>> test = new ArrayList<Map<String,String>>();
    	for(Map<String,String> singleApprover : checkApproveKind) {
//    		System.out.println(singleApprover);

    		if(singleApprover.get("user_update").equals(user)) {
    			thisApprover.put("doc_id", singleApprover.get("doc_id"));
    			thisApprover.put("user_update", singleApprover.get("user_update"));
    			thisApprover.put("approve_kind_code", singleApprover.get("approve_kind_code"));
    			thisApprover.put("lvl", singleApprover.get("lvl"));
    			thisApprover.put("time_update", singleApprover.get("time_update"));
    			thisApprover.put("result", singleApprover.get("result"));
    		}
    	}
//    	System.out.println(thisApprover.get("result"));
    	Boolean isTimeToApprove = true;
    	//Check coi duyet chua
    	if(thisApprover.get("result") != null) {
    		System.out.println("Nguoi nay duyet roi");
    	  	return ResponseEntity.status(200).body("Không thể duyệt");
    	}
    	//Check neu result cua chung tu da co nguoi duyet chua neu kind = OR
    	//Neu chua co ai duyet thi bat dau check lvl thap hon neu co
    	if(thisApprover.get("approve_kind_code").equals("OR")) {
    		for(Map<String,String> singleApprover : checkApproveKind) {
    			if(singleApprover.get("result") != null && singleApprover.get("lvl").equals(thisApprover.get("lvl"))) {
    				//return da co nguoi duyet neu kind = OR
    				System.out.println("(OR) Da co nguoi duyet o lvl: " +thisApprover.get("lvl"));
    				isTimeToApprove = false;
                	return ResponseEntity.status(200).body("Không thể duyệt");
    			}
    		}
    	}
    	//Check cac buoc duyet nho hon neu co:
    	//Neu trong buoc duyet nho hon kind = OR  => check chi can 1 cai result 
    	//Neu trong buoc duyet nho hon kind = MUS => check tat ca cac buoc duyet o cung cap da co result chua
    	List<Map<String,String>> smallerLvls = new ArrayList<Map<String, String>>();
    	int targetLevel = Integer.parseInt(thisApprover.get("lvl"));
    	System.out.println("target: "+targetLevel);
    	for(Map<String,String> singleApprover : checkApproveKind) {
    		for (int i = 1; i < targetLevel; i++) {
    			if (Integer.parseInt(singleApprover.get("lvl")) == i) {
//    			    System.out.print(singleApprover.get("lvl")+" ");
//    				System.out.print(singleApprover.get("user_update")+" ");
//    				System.out.println(singleApprover.get("approve_kind_code")+" ");
    				Map<String,String> smallerLvl = new HashMap<>();
    				smallerLvl.put("lvl",singleApprover.get("lvl"));
    				smallerLvl.put("user", singleApprover.get("user_update"));
    				smallerLvl.put("approve_kind_code", singleApprover.get("approve_kind_code"));
    				smallerLvl.put("result", singleApprover.get("result"));
    			 	smallerLvls.add(smallerLvl);
    			}
    		}
    	}
    	//List nguoi duyet cap nho hon
    	System.out.println(smallerLvls);
		
    	for (int i = 1; i < targetLevel; i++) {
    	    int count = 0; // Biến đếm
    	    int countStep = 0;
    	    String kind = " ";
	    	ArrayList<Object> a = new ArrayList<>();

    	    for (Map<String, String> smaller : smallerLvls) {
    	        if (Integer.parseInt(smaller.get("lvl")) == i) {
    	            // Nếu có một dòng có cùng lvl
    	            count++;
    	            if (smaller.get("approve_kind_code").equals("OR")) {
    	            	kind = "OR";
    	                System.out.println(smaller.get("lvl") + " " +smaller.get("user")+" "+ smaller.get("approve_kind_code")+ " "+smaller.get("result"));      
    	                if(smaller.get("result") != null && smaller.get("result").equals("0")) {
    	            		System.out.println(smaller.get("user")+" o cap duyet " + smaller.get("lvl")+" da tu choi");
    	                	isTimeToApprove = false;
    	                	return ResponseEntity.status(200).body("Không thể duyệt");

    	                }
    	                if(smaller.get("result") != null && !smaller.get("result").equals("0")) {
    	                	a.add(new Object());
    	                }
    	            } else if (smaller.get("approve_kind_code").equals("MUS")) {
    	            	kind = "MUS";
    	                System.out.println(smaller.get("lvl") + " " +smaller.get("user")+" "+ smaller.get("approve_kind_code")+ " "+smaller.get("result"));
    	            	if(smaller.get("result") != null && smaller.get("result").equals("0")) {
    	            		System.out.println(smaller.get("user")+" o cap duyet " + smaller.get("lvl")+" da tu choi");
    	            		isTimeToApprove = false;
    	                	return ResponseEntity.status(200).body("Không thể duyệt");
    	            	}
    	                if(smaller.get("result") != null && !smaller.get("result").equals("0")) {
    	                	a.add(new Object());
    	                }
    	            }
    	        }  
    	    }
    	    for(Object b : a) {
            	countStep++;
            }
            System.out.println("Level "+ i + " da duyet "+ countStep + " buoc");
    	    // Hiển thị số lần xuất hiện của lvl
    	    System.out.println("Level " + i + " co " + count + " buoc duyet " + kind);
    	    if ("OR".equals(kind)) {
    	        if (countStep > 0) {
    	            System.out.println("=> Level " + i + " da duyet");
    	        } else {
    	            System.out.println("=> Level " + i + " chua duyet du");
    	            isTimeToApprove = false;
    	        }
    	    } else if ("MUS".equals(kind)) {
    	        if (countStep == count) {
    	            System.out.println("=> Level " + i + " da duyet");
    	        } else {
    	            System.out.println("=> Level " + i + " chua duyet du");
    	            isTimeToApprove = false;
    	        }
    	    } else {
    	        System.out.println("Kind không hợp lệ");
    	    }
    	}

    	if(isTimeToApprove == false) {
    		System.out.println("==> Khong the duyet");
        	return ResponseEntity.status(200).body("Không thể duyệt");
    	}else {
    		System.out.println("==> Duyet di bro");
        	return ResponseEntity.status(200).body("Duyệt đi bồ tèo");
    	}
    	}catch(Exception e) {
        	return null;
    	}
    }
}

