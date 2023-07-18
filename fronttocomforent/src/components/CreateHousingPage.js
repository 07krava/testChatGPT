import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function CreateHousingPage() {
  const navigate = useNavigate();

  const [housingData, setHousingData] = useState({
    title: '',
    description: '',
    maxAmountPeople: 0,
    beds: 0,
    bedRooms: 0,
    bathRooms: 0,
    price: 0,
    housingType: '',
    isActive: true,
    location: {
      country: '',
      region: '',
      city: '',
      street: '',
      houseNumber: '',
      apartmentNumber: '',
      zipCode: '',
    },
    files: [],
  });

  const isUserAuthenticated = () => {
    // Здесь вам нужно реализовать проверку авторизации пользователя
    // Например, проверить наличие токена аутентификации в хранилище или выполнить другую проверку, используя вашу библиотеку аутентификации
    // Вернуть true, если пользователь авторизован, и false в противном случае
    // Ниже приведен пример простой проверки наличия токена в localStorage
    const token = localStorage.getItem('token');
    return !!token;
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setHousingData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleLocationChange = (e) => {
    const { name, value } = e.target;
    setHousingData((prevState) => ({
      ...prevState,
      location: {
        ...prevState.location,
        [name]: value,
      },
    }));
  };

  const handleFileChange = (e) => {
    const files = Array.from(e.target.files);
    setHousingData((prevState) => ({
      ...prevState,
      files,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!isUserAuthenticated()) {
      // Пользователь не авторизован, выведите сообщение об ошибке или выполните другие действия в зависимости от вашей логики
      console.log('Пользователь не авторизован');
      return;
    }

    const formData = new FormData();
    for (let i = 0; i < housingData.files.length; i++) {
      formData.append('files', housingData.files[i]);
    }
    formData.append('housing', JSON.stringify(housingData));

    try {
      const response = await axios.post('http://localhost:8080/api/user/add', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      console.log(response.data);
      // Здесь вы можете добавить обработку успешного создания жилья
      navigate('/success'); // Перенаправление на страницу успешного создания жилья
    } catch (error) {
      console.error(error);
      // Здесь вы можете добавить обработку ошибки
    }
  };

  return (
    <div>
      <h1>Create Housing</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Title:</label>
          <input
            type="text"
            name="title"
            value={housingData.title}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Description:</label>
          <textarea
            name="description"
            value={housingData.description}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Max Amount of People:</label>
          <input
            type="number"
            name="maxAmountPeople"
            value={housingData.maxAmountPeople}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Beds:</label>
          <input
            type="number"
            name="beds"
            value={housingData.beds}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Bedrooms:</label>
          <input
            type="number"
            name="bedRooms"
            value={housingData.bedRooms}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Bathrooms:</label>
          <input
            type="number"
            name="bathRooms"
            value={housingData.bathRooms}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Price:</label>
          <input
            type="number"
            name="price"
            value={housingData.price}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Housing Type:</label>
          <input
            type="text"
            name="housingType"
            value={housingData.housingType}
            onChange={handleChange}
          />
        </div>
        <div>
          <h2>Location:</h2>
          <div>
            <label>Country:</label>
            <input
              type="text"
              name="country"
              value={housingData.location.country}
              onChange={handleLocationChange}
            />
          </div>
          <div>
            <label>Region:</label>
            <input
              type="text"
              name="region"
              value={housingData.location.region}
              onChange={handleLocationChange}
            />
          </div>
          <div>
            <label>City:</label>
            <input
              type="text"
              name="city"
              value={housingData.location.city}
              onChange={handleLocationChange}
            />
          </div>
          <div>
            <label>Street:</label>
            <input
              type="text"
              name="street"
              value={housingData.location.street}
              onChange={handleLocationChange}
            />
          </div>
          <div>
            <label>House Number:</label>
            <input
              type="text"
              name="houseNumber"
              value={housingData.location.houseNumber}
              onChange={handleLocationChange}
            />
          </div>
          <div>
            <label>Apartment Number:</label>
            <input
              type="text"
              name="apartmentNumber"
              value={housingData.location.apartmentNumber}
              onChange={handleLocationChange}
            />
          </div>
          <div>
            <label>Zip Code:</label>
            <input
              type="text"
              name="zipCode"
              value={housingData.location.zipCode}
              onChange={handleLocationChange}
            />
          </div>
        </div>
        <div>
          <label>Files:</label>
          <input type="file" multiple onChange={handleFileChange} />
        </div>
        <button type="submit">Create Housing</button>
      </form>
    </div>
  );
}

export default CreateHousingPage;