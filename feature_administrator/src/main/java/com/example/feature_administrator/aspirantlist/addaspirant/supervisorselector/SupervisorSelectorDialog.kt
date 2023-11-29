package com.example.feature_administrator.aspirantlist.addaspirant.supervisorselector

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.model.Supervisor
import com.example.feature_administrator.databinding.SupervisorSelectorDialogBinding

class SupervisorSelectorDialog (
    private val supervisorList: List<Supervisor>,
    private val listener: (Supervisor) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val supervisorSelectorDialog = AlertDialog.Builder(requireContext())
        val supervisorSelectorCustomAlertDialogView = SupervisorSelectorDialogBinding.inflate(layoutInflater)

        val supervisorSelectorAdapter = SupervisorSelectorAdapter { supervisor ->
            listener.invoke(supervisor)
        }

        supervisorSelectorAdapter.listOfSupervisors = supervisorList

        with(supervisorSelectorCustomAlertDialogView.root) {
            adapter = supervisorSelectorAdapter
            layoutManager = LinearLayoutManager(context)
        }

        supervisorSelectorDialog.setView(supervisorSelectorCustomAlertDialogView.root)

        return supervisorSelectorDialog.create()
    }
}