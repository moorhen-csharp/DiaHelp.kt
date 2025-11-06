package dev.moorhen.diahelp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dev.moorhen.diahelp.R
import dev.moorhen.diahelp.view.adapters.CalculatorPagerAdapter

class CalculatorContainerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_calculator_container, container, false)

        val pager = view.findViewById<ViewPager2>(R.id.calculatorPager)
        val tabs = view.findViewById<TabLayout>(R.id.calculatorTabs)

        pager.adapter = CalculatorPagerAdapter(this)

        // Привязываем вкладки к страницам
        TabLayoutMediator(tabs, pager) { tab, pos ->
            tab.text = if (pos == 0) "Коррекция" else "Хлебные единицы"
        }.attach()

        return view
    }
}
