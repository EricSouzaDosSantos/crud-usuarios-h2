const apiURL = 'http://localhost:8080/usuarios';

document.getElementById('botao-form').addEventListener('click', function (event) {
    event.preventDefault();
    const botao = event.target;
    if (botao.innerText === 'Adicionar') {
        adicionarUsuario();
    }
});

// Carregar usuários ao iniciar página
window.onload = function () {
    carregarUsuarios();
}

// Listar usuários
function carregarUsuarios() {
    fetch(apiURL)
        .then(res => res.json())
        .then(data => {
            const tabela = document.getElementById('tabela-usuarios');
            tabela.innerHTML = '';

            data.forEach(usuario => {
                const linha = `
                <tr>
                    <td>${usuario.id}</td>
                    <td>${usuario.idAcesso}</td>
                    <td>${usuario.nome}</td>
                    <td>${usuario.telefone}</td>
                    <td>${usuario.email}</td>
                    <td><img src="../../../../../img/84d37520-3e99-42f9-8bc3-34571c7aecbf_main-img.jpeg" alt="foto"></td>
                    <td>
                        <button onclick="buscarUsuarioParaEditar(${usuario.id})">Editar</button>
                        <button onclick="apagarUsuario(${usuario.id})">Apagar</button>
                    </td>
                </tr>`;
                tabela.innerHTML += linha;
            });
        });
}

// Adicionar usuário
function adicionarUsuario() {
    const arquivo = document.getElementById('foto').files[0];
    const formData = new FormData();

    formData.append('idAcesso', document.getElementById('idAcesso').value);
    formData.append('nome', document.getElementById('nome').value);
    formData.append('telefone', document.getElementById('telefone').value);
    formData.append('email', document.getElementById('email').value);
    formData.append('foto', arquivo);

    fetch(apiURL, {
        method: 'POST',
        body: formData
    })
        .then(res => res.json())
        .then(() => {
            alert('Usuário adicionado com sucesso!');
            limparCampos();
            carregarUsuarios();
        })
        .catch(err => console.error('Erro ao adicionar usuário:', err));
}


// Limpa campos do formulário
function limparCampos(){
    document.getElementById('idAcesso').value = '';
    document.getElementById('nome').value = '';
    document.getElementById('telefone').value = '';
    document.getElementById('email').value = '';
    document.getElementById('foto').value = '';
    document.getElementById('botao-form').innerText = 'Adicionar';
    document.getElementById('botao-form').addEventListener('click', function (event) {
        event.preventDefault();
        const botao = event.target;
        if (botao.innerText === 'Adicionar') {
            adicionarUsuario();
        }
    });
}

// Editar usuário
function buscarUsuarioParaEditar(id) {

        fetch(apiURL+"/"+id)
            .then(res => res.json())
            .then(data => {
                document.getElementById('idAcesso').disabled = true;
                document.getElementById('idAcesso').value = data.idAcesso;
                document.getElementById('foto').value = data.foto;
                document.getElementById('nome').value = data.nome
                document.getElementById('email').value = data.email
                document.getElementById('telefone').value = data.telefone
                document.getElementById('botao-form').innerText = 'Editar';
                document.getElementById('botao-form').addEventListener('click', function (event) {
                    event.preventDefault();
                    const botao = event.target;
                    if (botao.innerText === 'Editar') {
                        editarUsuarioPeloId(id);
                    }
                });
            });
}

function editarUsuarioPeloId(id){
    const file = document.getElementById('foto');
    const data = new FormData();
    data.append('image', file.files[0]);

    const usuario = {
        nome: document.getElementById('nome').value,
        telefone: document.getElementById('telefone').value,
        email: document.getElementById('email').value,
        foto: data
    };
    console.log(usuario)
        fetch(`${apiURL}/${id}`, {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: usuario
        })
            .then(res => {
                if(res.ok){
                    alert('Usuário atualizado com sucesso!');
                    carregarUsuarios();
                } else {
                    alert('Erro ao atualizar usuário!');
                }
            });
    limparCampos()
}

// Apagar usuário
function apagarUsuario(id) {
    if(confirm('Deseja apagar este usuário?')){
        fetch(`${apiURL}/${id}`, {
            method: 'DELETE'
        })
        .then(res => {
            if(res.ok){
                alert('Usuário apagado com sucesso!');
                carregarUsuarios();
            } else {
                alert('Erro ao apagar usuário!');
            }
        });
    }
}
