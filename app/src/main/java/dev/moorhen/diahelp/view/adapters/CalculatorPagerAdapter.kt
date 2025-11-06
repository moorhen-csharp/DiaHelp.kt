package dev.moorhen.diahelp.view.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.moorhen.diahelp.view.fragments.BreadUnitFragment
import dev.moorhen.diahelp.view.fragments.CorrectionFragment

class CalculatorPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CorrectionFragment()
            else -> BreadUnitFragment()
        }
    }
}
