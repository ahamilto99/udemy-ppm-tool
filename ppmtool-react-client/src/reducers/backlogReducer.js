import {
  GET_BACKLOG,
  GET_PROJECT_TASK,
  DELETE_PROJECT_TASK
} from '../actions/types';

const initState = {
  projectTasks: [],
  projectTask: {}
};

export default function (state = initState, action) {
  switch (action.type) {
    case GET_BACKLOG:
      return {
        ...state,
        projectTasks: action.payload
      };
    case GET_PROJECT_TASK:
      return {
        ...state,
        projectTask: action.payload
      };
    case DELETE_PROJECT_TASK:
      return {
        ...state
      };
    default:
      return state;
  }
}
