package alves.ariel.loginvalidator

import alves.ariel.loginvalidator.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        emailFocusListener()
        passwordFocusListener()
        phoneFocusListener()

        binding.submitButton.setOnClickListener{submitForm()}
    }

    private fun submitForm()
    {
        binding.emailContainer.helperText = validEmail()
        binding.passwordContainer.helperText = validPassword()
        binding.phoneContainer.helperText = validPhone()

        val validEmail = binding.emailContainer.helperText == null
        val validPassword = binding.passwordContainer.helperText == null
        val validPhone = binding.phoneContainer.helperText == null

        if (validEmail && validPassword && validPhone)
            resetForm()
         else
             invalidForm()

    }

    private fun invalidForm()
    {
        var message = ""
        if (binding.emailContainer.helperText != null)
            message += "\nEmail: " + binding.emailContainer.helperText
        if (binding.passwordContainer.helperText != null)
            message += "\nSenha: " + binding.passwordContainer.helperText
        if (binding.phoneContainer.helperText != null)
            message += "\nNúmero: " + binding.phoneContainer.helperText

        AlertDialog.Builder(this)
            .setTitle("Formulário inválido")
            .setMessage(message)
            .setPositiveButton("Ok"){ _,_ ->
                // do nothing
            }
            .show()
    }

    private fun resetForm() {
        var message = "Email: " + binding.emailEditText.text
            message += "\nSenha: " + binding.passwordEditText.text
            message += "\nNúmero: " + binding.phoneEditText.text

        AlertDialog.Builder(this)
            .setTitle("Formulário enviado")
            .setMessage(message)
            .setPositiveButton("Ok"){ _,_ ->
                binding.emailEditText.text = null
                binding.passwordEditText.text = null
                binding.phoneEditText.text = null

                binding.emailContainer.helperText = getString(R.string.obrigatorio)
                binding.passwordContainer.helperText = getString(R.string.obrigatorio)
                binding.phoneContainer.helperText = getString(R.string.obrigatorio)
            }
            .show()
    }


    // Valida email verificando se o texto digitado segue os padrões de Endereço de Email. se n
    private fun emailFocusListener() {
        binding.emailEditText.setOnFocusChangeListener { _, focused ->
            if (!focused)
            {
                binding.emailContainer.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String?
    {
        val emailText = binding.emailEditText.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches())
        {
            return "Endereço de email inválido"
        }
        return null
    }

    // Valida senha verificando se o texto digitado segue regra de negócio.
    private fun passwordFocusListener() {
        binding.passwordEditText.setOnFocusChangeListener { _, focused ->
            if (!focused)
            {
                binding.passwordContainer.helperText = validPassword()
            }
        }
    }

    // Valida campo senha verificando se o texto digitado segue os padrões de senha.
    private fun validPassword(): String?
    {
        val passwordText = binding.passwordEditText.text.toString()
        if(passwordText.length < 8)
        {
            return "Senha deve conter no mínimo 8 Caracteres"
        }
        if(!passwordText.matches(".*[A-Z].*".toRegex()))
        {
            return "Deve ter pelo menos 1 letra maiúscula"
        }
        if(!passwordText.matches(".*[a-z].*".toRegex()))
        {
            return "Deve ter pelo menos 1 letra minúscula"
        }
        if(!passwordText.matches(".*[@#$%^&=].*".toRegex()))
        {
            return "Deve ter pelo menos 1 caractere especial (@#\$%^&=)"
        }
        return null
    }

    // Valida numero de telefone verificando se o texto digitado segue regra de negócio.

    private fun phoneFocusListener() {
        binding.phoneEditText.setOnFocusChangeListener { _, focused ->
            if (!focused)
            {
                binding.phoneContainer.helperText = validPhone()
            }
        }
    }

    // Valida campo senha verificando se o texto digitado segue os padrões de senha.

    private fun validPhone(): String?
    {
        val phoneText = binding.phoneEditText.text.toString()

        if(!phoneText.matches(".*[0-9].*".toRegex()))
        {
            return "Número deve conter apenas dígitos"
        }
        if(phoneText.length != 11)
        {
            return "Número deve conter 11 dígitos"
        }
        return null
    }


}