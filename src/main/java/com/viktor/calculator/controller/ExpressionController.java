package com.viktor.calculator.controller;

import com.viktor.calculator.dto.ExpressionDto;
import com.viktor.calculator.entity.Expression;
import com.viktor.calculator.service.ExpressionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("/expressions")
@RequiredArgsConstructor
public class ExpressionController {

    private final ExpressionService expressionService;

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("expressionDto", new ExpressionDto());
        return "addExpression";
    }

    @PostMapping("/add")
    public String processAdd(@Valid @ModelAttribute("expressionDto") ExpressionDto expression, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addExpression";
        }
        expressionService.saveExpression(expression);
        return "redirect:/expressions";
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("expressions", expressionService.findAllExpressions());
        return "index";
    }

    @GetMapping("/equals")
    public String getExpressionsWhereResultEquals(@RequestParam(required = false) String value, Model model) {
        if (value == null) {
            model.addAttribute("expressions", expressionService.findAllExpressions());
        } else {
            double v = Double.parseDouble(value);
            model.addAttribute("expressions", expressionService.findExpressionsWhereResultEquals(v));
        }
        return "equals";
    }

    @GetMapping("/greaterThan")
    public String getExpressionsWhereResultGreaterThan(@RequestParam(required = false) String value, Model model) {
        if (value == null) {
            model.addAttribute("expressions", expressionService.findAllExpressions());
        } else {
            double v = Double.parseDouble(value);
            model.addAttribute("expressions", expressionService.findExpressionsWhereResultIsGreaterThan(v));
        }
        return "greater";
    }

    @GetMapping("/lessThan")
    public String getExpressionsWhereResultLessThan(@RequestParam(required = false) String value, Model model) {
        if (value == null) {
            model.addAttribute("expressions", expressionService.findAllExpressions());
        } else {
            double v = Double.parseDouble(value);
            model.addAttribute("expressions", expressionService.findExpressionsWhereResultIsLessThan(v));
        }
        return "less";
    }

    @GetMapping("/edit/{id}")
    public String getById(@PathVariable("id") Long id, Model model) {
        Expression expressionById = expressionService.findExpressionById(id);
        model.addAttribute("expression", expressionById);
        return "update-expression";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        model.addAttribute("expression", new Expression());
        return "update-expression";
    }

    @PostMapping("/update/{id}")
    public String updateProcess(@PathVariable("id") Long id, @Valid @ModelAttribute("expression") ExpressionDto expression, Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "update-expression";
        }
        expressionService.updateExpression(id, expression);
        model.addAttribute("expressions", expressionService.findAllExpressions());
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        expressionService.deleteById(id);
        model.addAttribute("expressions", expressionService.findAllExpressions());
        return "index";
    }

}
