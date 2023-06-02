package com.example.feature_supervisor_research.aspirantlist

import android.app.Dialog
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.feature_supervisor_research.databinding.ChangeGradeDialogBinding

class ChangeGradeDialog(
    val grade: Int,
    val listener: (Int) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialog = AlertDialog.Builder(requireContext())

        val binding = ChangeGradeDialogBinding.inflate(layoutInflater)
        var newGrade = grade

        binding.apply {
            seekBar.progress = grade
            progressTextView.text = "Оцінка: $grade"

            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    progressTextView.text = "Оцінка: $progress"
                    newGrade = progress
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {
                    // Дії, які необхідно виконати при початку переміщення ползунка
                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    // Дії, які необхідно виконати при завершенні переміщення ползунка
                }
            })
            okButton.setOnClickListener {
                listener.invoke(newGrade)
                dismiss()
            }
        }



        alertDialog.setView(binding.root)

        return alertDialog.create()
    }
}
