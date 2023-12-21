package com.android.example.primordialspirit;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DamageCalcActivity extends AppCompatActivity {

    private EditText levelEditText, baseAttackEditText, equipmentAttackEditText,
            attackBonusEditText, criticalRateEditText, criticalDamageEditText;
    private Spinner elementalReactionSpinner;
    private TextView totalDamageTextView;

    // 基础攻击力
    private double baseAttack = 0.0;

    // 装备攻击力
    private double equipmentAttack = 0.0;

    // 攻击力加成
    private double attackBonus = 0.0;

    // 元素反应加成
    private double elementalReactionMultiplier = 1.0;

    // 暴击率
    private double criticalRate = 0.0;

    // 暴击伤害
    private double criticalDamage = 2.0; // 默认为2.0倍暴击伤害

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_damage_calc);

        levelEditText = findViewById(R.id.levelEditText);
        baseAttackEditText = findViewById(R.id.baseAttackEditText);
        equipmentAttackEditText = findViewById(R.id.equipmentAttackEditText);
        attackBonusEditText = findViewById(R.id.attackBonusEditText);
        criticalRateEditText = findViewById(R.id.criticalRateEditText);
        criticalDamageEditText = findViewById(R.id.criticalDamageEditText);
        elementalReactionSpinner = findViewById(R.id.elementalReactionSpinner);
        totalDamageTextView = findViewById(R.id.totalDamageTextView);

        // 设置元素反应选项的下拉列表
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.elemental_reactions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        elementalReactionSpinner.setAdapter(adapter);

        // 添加事件监听器
        addListeners();
    }

    private void addListeners() {
        levelEditText.addTextChangedListener(new DamageTextWatcher());
        baseAttackEditText.addTextChangedListener(new DamageTextWatcher());
        equipmentAttackEditText.addTextChangedListener(new DamageTextWatcher());
        attackBonusEditText.addTextChangedListener(new DamageTextWatcher());
        criticalRateEditText.addTextChangedListener(new DamageTextWatcher());
        criticalDamageEditText.addTextChangedListener(new DamageTextWatcher());

        elementalReactionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                // 根据选择的元素反应更新加成值
                switch (position) {
                    case 0: // 超导
                        elementalReactionMultiplier = 1.2; // 例如，超导加成为1.2
                        break;
                    case 1: // 融化
                        elementalReactionMultiplier = 1.5; // 例如，融化加成为1.5
                        break;
                    case 2: // 蒸发
                        elementalReactionMultiplier = 1.8; // 例如，蒸发加成为1.8
                        break;
                    // 可以根据实际需要添加更多的元素反应处理
                }
                calculateTotalDamage();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });
    }

    private class DamageTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void afterTextChanged(Editable editable) {
            calculateTotalDamage();
        }
    }

    private void calculateTotalDamage() {
        // 获取用户输入的值
        try {
            baseAttack = Double.parseDouble(baseAttackEditText.getText().toString());
            equipmentAttack = Double.parseDouble(equipmentAttackEditText.getText().toString());
            attackBonus = Double.parseDouble(attackBonusEditText.getText().toString());
            criticalRate = Double.parseDouble(criticalRateEditText.getText().toString());
            criticalDamage = Double.parseDouble(criticalDamageEditText.getText().toString());
        } catch (NumberFormatException e) {
            // 处理无效输入
            e.printStackTrace();
        }

        // 计算总伤害
        double totalDamage = calculateDamage();
        totalDamageTextView.setText(String.format("总伤害: %.2f", totalDamage));
    }

    private double calculateDamage() {
        // 在这里添加你的伤害计算逻辑
        // 根据输入的各项参数计算总伤害
        double totalDamage = (baseAttack + equipmentAttack) * (1 + 0.1*attackBonus) *
                elementalReactionMultiplier * (1 + 0.1*criticalRate * criticalDamage);

        return (int)totalDamage;
    }
}
