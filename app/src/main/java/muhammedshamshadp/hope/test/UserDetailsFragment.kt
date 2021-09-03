package muhammedshamshadp.hope.test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.google.gson.internal.bind.util.ISO8601Utils
import muhammedshamshadp.hope.test.databinding.FragmentUserDetailsBinding
import muhammedshamshadp.hope.test.databinding.FragmentUserHomeBinding
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

class UserDetailsFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    lateinit var binding: FragmentUserDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }
    private fun init() {
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
                    .load(GlideUrl(this.picture.medium))
                    .placeholder(R.drawable.ic_account)
                    .into(binding.imageUser)
                binding.userAddressDetails.text =
                    this.location.street.number.toString() + " " + this.location.street.name + "\n" +
                            this.location.city + " "  + "\n" +
                            this.location.state+","+this.location.postcode+ "\n" +
                            this.location.country

            }
            binding.doneBtn.setOnClickListener {
                Navigation.findNavController(requireView()).popBackStack()
            }
        }
    }
}