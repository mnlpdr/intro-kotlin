package br.ifpb.pdm

fun main() {
    val repositorioAnimal = RepositorioAnimal()
    var opcao = 0
    var nome=""
    var idade = 0
    var cor = Cores.INDEFINIDO

    while (opcao != 11) {
        menu()
        print("Digite a opção: ")
        opcao = readlnOrNull()?.toInt() ?: 0

        if(opcao in 1..4){
            println("Digite o nome:")
            nome = readlnOrNull().toString()

            println("Digite a idade:")
            idade = readlnOrNull()?.toInt() ?: 0

            println("Digite a cor:")
            try{
                val corInformada = readlnOrNull().toString().uppercase()
                cor = Cores.valueOf(corInformada)
            }
            catch (e:IllegalArgumentException){
                println("A cor informada é inválida.")
                continue
            }

        }

        when (opcao) {

            1 -> {
                val cachorro = Cachorro(idade, cor)
                cachorro.nome = nome
                repositorioAnimal.adicionar(cachorro)
            }
            2 -> {
                val gato = Gato(idade, cor)
                gato.nome = nome
                repositorioAnimal.adicionar(gato)
            }
            3 -> {
                val passaro = Passaro(idade, cor)
                passaro.nome = nome
                repositorioAnimal.adicionar(passaro)
            }
            4 ->{
                val homem = Homem(idade, cor)
                homem.nome = nome
                repositorioAnimal.adicionar(homem)
            }
            5 -> {
                repositorioAnimal.listar()
            }
            6 -> {
                repositorioAnimal.animais.forEach(Animal::emitirSom)
                repositorioAnimal.animais.forEach { it.emitirSom()}
            }
            7 -> {
                println("Digite o nome do animal a ser removido:")
                repositorioAnimal.remover(readln().uppercase())
            }
            8 -> {
                println("Digite a cor dos animais que você deseja listar:")
                val cor = readlnOrNull()?.uppercase()?.let {Cores.valueOf(it)}
                cor?.let {repositorioAnimal.listarPorCor(it)}

            }
            9 -> {
                println("Digite a idade dos animais que você deseja listar: ")
                val idade = readlnOrNull()?.toIntOrNull()
                idade?.let { repositorioAnimal.listarPorIdade(it) }
            }
            10 -> {
                println("Digite o nome do animal que você quer buscar: ")
                nome = readlnOrNull()?.uppercase() ?: ""
                val animalBuscado = repositorioAnimal.buscarPorNome(nome)
                if (animalBuscado != null){
                    println("O animal $nome foi encontrado")
                }
                else{
                    println("O animal $nome não foi encontrado")

                }

            }

            }
        }

}


abstract class Animal(var idade: Int, val cor: Cores) {
    open var nome: String = ""
        get() = "Animal: $field"
        set(valor) {
            field = valor
        }

    abstract fun emitirSom()

    open fun idadeEmAnosHumanos(): Int {
        return this.idade * 7
    }

    override fun toString(): String {
        return "Nome: $nome Idade: $idade Cor: $cor"
    }

}

class Cachorro(idade: Int, cor: Cores) : Animal(idade, cor) {
    override var nome: String = ""
        get() = field
        set(valor) {
            field = valor
        }
    override fun emitirSom() {
        println("Au au")
    }
}
class Gato(idade: Int, cor: Cores) : Animal(idade, cor) {
    override fun emitirSom() {
        println("Miau")
    }
}

class Passaro(idade: Int, cor: Cores) : Animal(idade, cor) {
    override fun emitirSom() {
        println("Piu piu")
    }
}

class Homem(idade: Int, cor: Cores) : Animal (idade, cor){
    override fun emitirSom() {
        println("Olá.")
    }

    override fun idadeEmAnosHumanos(): Int {
        return this.idade
    }

}

enum class Cores (val cor: String){
    RAJADO("Rajado"),
    PRETO("Preto"),
    AMARELO("Amarelo"),
    INDEFINIDO("Indefinido")
}

fun menu() {

    println("1 - Criar Cachorro")
    println("2 - Criar Gato")
    println("3 - Criar Pássaro")
    println("4 - Criar Homem")
    println("5 - Listar Animais")
    println("6 - Emitir som")
    println("7 - Remover um Animal")
    println("8 - Listar animais por cor")
    println("9 - Listar animais por idade")
    println("10 - Buscar animal pelo nome")
    println("11 - Sair")
}

class RepositorioAnimal {
    val animais: MutableList<Animal> = mutableListOf()

    fun adicionar(animal: Animal) {
        animais.add(animal)
    }

    fun listar() {
        animais.forEach { println(it.nome) }
    }

    fun remover(nome: String) {
        val index = this.getIndexNome(nome)
        this.animais.removeAt(index)
    }
    fun getIndexNome(nome: String): Int{
        val index = this.animais.indexOfFirst {  it.nome.equals(nome, ignoreCase = true)}
        if (index == -1){
            throw IllegalArgumentException("Não foi encontrado um animal com esse nome.")
        }
        return index
    }

    fun buscarPorNome(nome: String): Animal? {
       val index = this.animais.indexOfFirst { it.nome.equals(nome, ignoreCase = true) }
       val animal = if (index != -1) this.animais[index] else null;
       return animal

    }



    fun listarPorCor(cor: Cores) {
        val animaisPorCor = animais.filter { it.cor == cor }
        if (animaisPorCor.isNotEmpty()){
            animaisPorCor.forEach{println(it.nome)}
        } else {
            println("Não há animais da cor $cor na lista.")
        }

    }

    fun listarPorIdade(idade: Int){
        val animaisPorIdade = animais.filter {it.idade == idade}
        if (animaisPorIdade.isNotEmpty()){
            animaisPorIdade.forEach {println(it.idade)}
        } else {
            println("Não há animais com $idade anos na lista.")
        }
    }

}

