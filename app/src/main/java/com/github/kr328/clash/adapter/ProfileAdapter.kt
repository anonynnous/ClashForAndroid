package com.github.kr328.clash.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.recyclerview.widget.RecyclerView
import com.github.kr328.clash.R
import com.github.kr328.clash.service.data.ClashProfileEntity
import com.github.kr328.clash.view.RadioFatItem

class ProfileAdapter(private val context: Context, private val listener: (Int) -> Unit) :
    RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {
    var profiles: List<ClashProfileEntity> = emptyList()

    class ProfileViewHolder(val view: RadioFatItem) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        return ProfileViewHolder(
            RadioFatItem(context).apply {
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            }
        )
    }

    override fun getItemCount(): Int {
        return profiles.size
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val current = profiles[position]

        holder.view.title = current.name
        holder.view.summary = context.getString(
            R.string.clash_profile_item_summary,
            current.proxies, current.proxyGroups, current.rules
        )
        holder.view.isChecked = current.selected
        holder.view.setOnClickListener {
            listener(current.id)
        }

        with(current.token) {
            when {
                startsWith("http") || startsWith("content") -> {
                    holder.view.operation = context.getDrawable(R.drawable.ic_profile_refresh)
                }
                startsWith("file") -> {
                    holder.view.operation = context.getDrawable(R.drawable.ic_profile_edit)
                }
            }
        }
    }
}