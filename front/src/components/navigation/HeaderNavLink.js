import { NavLink } from "react-router-dom";

const HeaderNavLink = (props) => {
  return (
    <li key={props.liKey} className={props.className}>
      <NavLink to={props.to}>
        <p>{props.label}</p>
      </NavLink>
    </li>
  );
};

export default HeaderNavLink;
