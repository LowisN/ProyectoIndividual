/**
 * Gestiona el modo oscuro para toda la aplicación
 */
document.addEventListener('DOMContentLoaded', function() {
    console.log("Dark mode script inicializado"); // Log para depuración
    
    // Buscar o crear el botón de cambio de tema
    let themeToggle = document.getElementById('theme-toggle');
    
    // Si el botón no existe en la página, lo creamos y añadimos al header
    if (!themeToggle) {
        console.log("Botón de tema no encontrado, intentando crearlo"); // Log para depuración
        const header = document.querySelector('.header');
        if (header) {
            console.log("Header encontrado, agregando botón de tema"); // Log para depuración
            themeToggle = document.createElement('button');
            themeToggle.id = 'theme-toggle';
            themeToggle.className = 'theme-toggle';
            themeToggle.title = 'Cambiar tema';
            themeToggle.innerHTML = '🌙'; // Valor predeterminado para modo claro
            themeToggle.style.cssText = 'position: absolute; right: 20px; top: 50%; transform: translateY(-50%); font-size: 1.8rem; cursor: pointer; z-index: 1000; background-color: rgba(255, 255, 255, 0.25); border: none; color: white; display: flex; align-items: center; justify-content: center; width: 45px; height: 45px; border-radius: 50%;';
            header.appendChild(themeToggle);
            console.log("Botón de tema creado y agregado al header"); // Log para depuración
        } else {
            console.log("Header no encontrado, no se pudo crear el botón de tema"); // Log para depuración
        }
    } else {
        console.log("Botón de tema encontrado"); // Log para depuración
    }
    
    if (themeToggle) {
        const body = document.body;
        
        // Verificar si hay una preferencia guardada
        const darkMode = localStorage.getItem('darkMode') === 'true';
        console.log("Preferencia de modo oscuro:", darkMode); // Log para depuración
        
        // Aplicar el tema según la preferencia
        if (darkMode) {
            body.classList.add('dark-mode');
            themeToggle.innerHTML = '☀️'; // Sol para modo oscuro
            console.log("Modo oscuro aplicado"); // Log para depuración
        } else {
            body.classList.remove('dark-mode');
            themeToggle.innerHTML = '🌙'; // Luna para modo claro
            console.log("Modo claro aplicado"); // Log para depuración
        }
        
        // Manejar el cambio de tema
        themeToggle.addEventListener('click', function() {
            body.classList.toggle('dark-mode');
            
            const isDarkMode = body.classList.contains('dark-mode');
            localStorage.setItem('darkMode', isDarkMode);
            
            // Cambiar el icono según el modo
            themeToggle.innerHTML = isDarkMode ? '☀️' : '🌙';
            console.log("Tema cambiado a:", isDarkMode ? "oscuro" : "claro"); // Log para depuración
        });
    }
    
    // Si hay una preferencia previa pero no hay botón, aplicar el tema de todos modos
    if (!themeToggle && localStorage.getItem('darkMode') === 'true') {
        document.body.classList.add('dark-mode');
        console.log("Aplicando modo oscuro sin botón visible"); // Log para depuración
    }
});
