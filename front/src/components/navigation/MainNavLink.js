import { NavLink } from "react-router-dom";

const MainNavLink = (props) => {
  return (
    <li key={props.liKey}>
      <NavLink to={props.to}>
        <p
          onMouseEnter={() => props.onHoverIn(props.activeIndex)}
          onClick={props.onLinkClick}
        >
          {props.label}
        </p>
      </NavLink>
    </li>
  );
};

export default MainNavLink;
