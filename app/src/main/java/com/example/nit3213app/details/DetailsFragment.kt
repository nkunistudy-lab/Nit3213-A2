package com.example.nit3213app.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.nit3213app.R
import com.example.nit3213app.model.Entity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val entity = arguments?.getParcelable<Entity>("entity")

        val titleText = view.findViewById<TextView>(R.id.detailTitle)
        val descriptionText = view.findViewById<TextView>(R.id.detailDescription)
        val propertiesText = view.findViewById<TextView>(R.id.detailProperties)

        if (entity != null) {
            val description = entity.properties["description"] ?: ""
            val otherProperties = entity.properties.filter { it.key != "description" }

            val firstEntry = otherProperties.entries.firstOrNull()
            titleText.text = firstEntry?.value ?: "Details"

            descriptionText.text = description

            val propertiesString = otherProperties.entries.joinToString("\n\n") {
                "${it.key.replaceFirstChar { c -> c.uppercase() }}:  ${it.value}"
            }
            propertiesText.text = propertiesString
        }
    }
}