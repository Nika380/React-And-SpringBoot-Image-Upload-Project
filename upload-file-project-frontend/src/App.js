import { useState } from 'react';
import './App.css';

function App() {
    const [file, setFile] = useState(new Blob());
    const [data, setData] = useState({ image: null, text: '' });
    const [link, setLink] = useState('');

    const handleFileChange = (e) => {
        const selectedFile = e.target.files[0];
        setFile(selectedFile);
        const container = document.getElementById('App');
        setData({ ...data, image: URL.createObjectURL(e.target.files[0]) });
        if (selectedFile) {
            const imageUrl = URL.createObjectURL(selectedFile);
            container.style.backgroundImage = `url(${imageUrl})`;
        }
    };

    const uploadFile = () => {
        const formData = new FormData();
        formData.append('file', file);
        fetch('http://localhost:8080/api/upload', {
            method: 'POST',
            body: formData,
        })
            .then(response => response.json())
            .then(data => {
                    console.log('Success!');
                    console.log(data);
                    setFile(new Blob());
                    alert('File Uploaded Successfully');
                    const downloadUrl = data.fileName;
                    setLink(downloadUrl);

            })
            .catch((error) => {
                console.error(error)
            })
    }

    console.log(file.size);


    return (
        <>
            <div className="container">
                <h1>Upload File</h1>
                <input type="file" id="file" onChange={handleFileChange} />
                {file.size > 0  && (
                    <button className="upload" onClick={uploadFile}>
                        Upload
                    </button>
                )}
            </div>
            <div className="App" id="App">
                {file.size === 0 && <p>Choose Image To Upload</p>}
            </div>
            <h1>Recently Uploaded File Download Link: {link}</h1>
        </>
    );
}

export default App;
