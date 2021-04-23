package com.mads.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mads.calculator.databinding.HistoryBottomSheetBinding

public class HistoryBottomSheetFragment() : BottomSheetDialogFragment() {

    private lateinit var binding: HistoryBottomSheetBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.history_bottom_sheet, container, true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        setupViews()
    }

    private fun setupViews(){
        binding.historyList.layoutManager = LinearLayoutManager(activity)
        binding.historyList.adapter =
            HistoryAdapter(InMemoryHistoryCache.getAll(), { expression ->
                viewModel.itemSelected(expression)
                dismiss()
            })
    }

    private class HistoryVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val historyText = itemView.findViewById<TextView>(R.id.history_text)
    }

    private class HistoryAdapter(
        private val items: List<Expression>,
        private val itemClick: (Expression) -> Unit
    ) : RecyclerView.Adapter<HistoryVH>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryVH {
            val rootView =
                LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
            return HistoryVH(rootView)
        }

        override fun onBindViewHolder(holder: HistoryVH, position: Int) {
            val expression = items.get(position)
            val text = holder.historyText.context.getString(
                R.string.history_item_format,
                expression.toDisplayString(),
                Calculator.computeExpression(expression)
            )
            holder.historyText.setText(text)
            holder.historyText.setOnClickListener { itemClick.invoke(expression) }
        }

        override fun getItemCount(): Int {
            return items.size
        }
    }

}