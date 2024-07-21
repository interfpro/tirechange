import {Helper} from './Helper.js';

export const Queries = {
  getAvailableTimes(from, until, workshop, type) {
    return fetch('http://localhost:8080/times?from=' + from + '&until=' + until + '&workshop=' + workshop + '&type=' + type)
    .then(response => {
      return response.json();
    }).then(res => {
      return res.map(item => {
        return {
          id: item.id,
          time: Helper.formatToDateTime(item.time),
          workshop: item.workshop,
          address: item.address,
          type: item.type
        };
      })
    })
  },

  postEntries(id, workshop, address) {
    const data = JSON.stringify({
      id: id,
      workshop: workshop,
      contactInformation: address
    }
    );
       return fetch("http://localhost:8080/book", {
         method: 'POST',
         headers: {
           'Content-type': 'application/json'
         },
         body: data
       }).then(response => {
         if(response.ok) {
           return response.ok;
         }
         throw new Error("Request failed!");
    });
  },

  getWorkshops() {
    return fetch('http://localhost:8080/workshops')
    .then(response => {
      return response.json();
    }).then(res => {
      return res.map(item => {
        return {
          id: item.id,
          name: item.name,
          address: item.address,
          types: item.types
        };
      })
    })
  },

  editWorkshop(id, name, address, types) {
    const data = JSON.stringify({
      id: id,
      name: name,
      address: address,
      types: types,
    }
    );
       return fetch("http://localhost:8080/edit-workshop", {
         method: 'PUT',
         headers: {
           'Content-type': 'application/json'
         },
         body: data
       }).then(response => {
         if(response.ok) {
           return response.ok;
         }
         throw new Error("Request failed!");
    });
  },

}
