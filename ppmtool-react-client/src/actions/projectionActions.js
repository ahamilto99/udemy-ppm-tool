import axios from 'axios';
import { GET_ERRORS, GET_PROJECT, GET_PROJECTS, DELETE_PROJECT } from './types';

export const createProject = (project, history) => async (dispatch) => {
  try {
    await axios.post('/api/project', project);
    history.push('/dashboard');
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

export const getProjects = () => async (dispatch) => {
  const result = await axios.get('/api/project');
  dispatch({
    type: GET_PROJECTS,
    payload: result.data
  });
};

export const getProject = (identifier, history) => async (dispatch) => {
  try {
    const result = await axios.get(`/api/project/${identifier}`);
    dispatch({
      type: GET_PROJECT,
      payload: result.data
    });
  } catch (e) {
    history.push('/dashboard');
  }
};

export const deleteProject = (identifier) => async (dispatch) => {
  if (
    window.confirm(
      'Are you sure you want to delete this project and all its data?'
    )
  ) {
    await axios.delete(`/api/project/${identifier}`);
    dispatch({
      type: DELETE_PROJECT,
      payload: identifier
    });
  }
};
