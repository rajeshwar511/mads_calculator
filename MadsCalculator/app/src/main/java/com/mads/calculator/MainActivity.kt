package com.mads.calculator

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mads.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        initViews()
        setupViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.history && InMemoryHistoryCache.getAll().isNotEmpty()) {
            val fragment = HistoryBottomSheetFragment()
            fragment.show(supportFragmentManager, HistoryBottomSheetFragment::class.simpleName)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewModel() {
        viewModel.smartAnswerLiveData.observe(
            this,
            Observer(binding.calculatorSmartAnswer::setText)
        )
        viewModel.calculatorTextLiveData.observe(this, Observer(binding.calculatorDisplay::setText))
    }

    private fun initViews() {
        binding.digit0.setOnClickListener { viewModel.addDigit(0) }
        binding.digit1.setOnClickListener { viewModel.addDigit(1) }
        binding.digit2.setOnClickListener { viewModel.addDigit(2) }
        binding.digit3.setOnClickListener { viewModel.addDigit(3) }
        binding.digit4.setOnClickListener { viewModel.addDigit(4) }
        binding.digit5.setOnClickListener { viewModel.addDigit(5) }
        binding.digit6.setOnClickListener { viewModel.addDigit(6) }
        binding.digit7.setOnClickListener { viewModel.addDigit(7) }
        binding.digit8.setOnClickListener { viewModel.addDigit(8) }
        binding.digit9.setOnClickListener { viewModel.addDigit(9) }

        binding.operatorMultiplication.setOnClickListener { viewModel.addOperator(OperatorType.MULTIPLICATION) }
        binding.operatorPlus.setOnClickListener { viewModel.addOperator(OperatorType.ADDITION) }
        binding.operatorDivision.setOnClickListener { viewModel.addOperator(OperatorType.DIVISION) }
        binding.operatorMinus.setOnClickListener { viewModel.addOperator(OperatorType.SUBTRACTION) }

        binding.operatorEquals.setOnClickListener { viewModel.computeAnswer() }
        binding.backSpace.setOnClickListener { viewModel.deleteLastPart() }
        binding.operatorClear.setOnClickListener { viewModel.clearExpression() }
    }
}