package com.example.spring_boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.spring_boot.entity.*;
import com.example.spring_boot.repository.InsurancePlanDAO;
import com.example.spring_boot.repository.RequestInsuranceDAO;

import java.util.List;
@Controller
public class insurance {
    @GetMapping("/insurance")
    public String getinsuranceplans(Model model,HttpSession session,@RequestParam Long carId) {

        List<InsurancePlan> i= InsurancePlanDAO.getAllInsurancePlans();
        model.addAttribute("carId",carId);
        model.addAttribute("i",i);
        boolean found=RequestInsuranceDAO.isCarInsured(carId);
        if(found){
            Insurance i2=RequestInsuranceDAO.getInsuranceByCarId(carId);
            InsurancePlan p2=InsurancePlanDAO.getInsurancePlanById(i2.getPlanId());
            model.addAttribute("plan",p2);
            model.addAttribute("insure", i2);
            return "insurance_there";
        }
        found=RequestInsuranceDAO.doesCarExistInRequestInsurance(carId);
        if(found){
            System.out.println(carId+"&&&");
            int p=RequestInsuranceDAO.getPlanIdFromRequestInsurance(carId);
            System.out.println(p+"&&&");
            InsurancePlan p2=InsurancePlanDAO.getInsurancePlanById(p);
            model.addAttribute("plan",p2);
            return "insure_request_there";
        }
        return "insurance_plan";
    }
    @PostMapping("/insurance")
public String selectPlan(@RequestParam("planId") int planId, @RequestParam("carId") Long carId) {
    // Add the selected plan ID to the model
    RequestInsuranceDAO.addRequestInsurance(planId,carId);
    return "redirect:/listcar"; // Redirect to a confirmation page or return a view
}
    @GetMapping("/cancel-request")
    public String takeRequestBack(@RequestParam("carId") Long carId){
        RequestInsuranceDAO.deleteEntryByCarId(carId);
        return "redirect:/listcar";
    }
}
