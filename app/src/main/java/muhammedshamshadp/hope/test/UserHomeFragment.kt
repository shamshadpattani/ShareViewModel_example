package muhammedshamshadp.hope.test

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.google.gson.internal.bind.util.ISO8601Utils
import muhammedshamshadp.hope.test.databinding.FragmentUserHomeBinding
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*


class UserHomeFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    lateinit var binding: FragmentUserHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        init()
    }

    private fun init() {
        binding.imageRest.setOnClickListener {
            viewModel.getData()
        }
        binding.detailsBtn.setOnClickListener {
                val action= UserHomeFragmentDirections.actionUserHomeFragmentToUserDetailsFragment()
            findNavController().navigate(action)
        }
    }



    private fun observe() {
        viewModel.userData.observe(viewLifecycleOwner){ it->
            it.results[0].apply {

               val dt= ISO8601Utils.parse(this.dob.date , ParsePosition(0))
                val utc = TimeZone.getTimeZone("UTC")
                val sourceFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy")
                val destFormat = SimpleDateFormat("dd MMM  yyyy ")
                sourceFormat.timeZone = utc
                val convertedDate = sourceFormat.parse(dt.toString())
                binding.userId.text=this.id.name+" "+this.id.value
                binding.userDob.text=destFormat.format(convertedDate).toString()
                binding.userName.text=this.name.title+"."+this.name.first+" "+this.name.last
                binding.userEmail.text=this.email
                Glide.with(requireContext())
                    .load(GlideUrl(this.picture.large))
                    .placeholder(R.drawable.ic_account)
                    .into(binding.imageUser)
            }


        }
    }


    companion object {

    }
}